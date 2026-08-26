import 'dart:io';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:image_picker/image_picker.dart';
import 'package:intl/intl.dart';
import '../../../core/theme/app_colors.dart';
import '../../../models/customer/customer_models.dart';
import '../../../providers/auth_provider.dart';
import '../../../providers/reference_data_provider.dart';
import '../widgets/neon_button.dart';
import '../widgets/neon_text_field.dart';

class RegisterScreen extends ConsumerStatefulWidget {
  const RegisterScreen({super.key});

  @override
  ConsumerState<RegisterScreen> createState() => _RegisterScreenState();
}

class _RegisterScreenState extends ConsumerState<RegisterScreen> {
  final _formKey = GlobalKey<FormState>();
  
  // Controllers
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  final _nameController = TextEditingController();
  final _phoneController = TextEditingController();
  final _dobController = TextEditingController();
  
  // Address Controllers
  final _holdingController = TextEditingController();
  final _areaController = TextEditingController();
  final _postalController = TextEditingController();

  final _permHoldingController = TextEditingController();
  final _permAreaController = TextEditingController();
  final _permPostalController = TextEditingController();

  Gender _selectedGender = Gender.MALE;
  CustomerOccupation _selectedOccupation = CustomerOccupation.STUDENT;
  DateTime? _selectedDob;
  
  int? _selectedDivisionId;
  int? _selectedDistrictId;
  int? _selectedPoliceStationId;

  int? _selectedPermDivisionId;
  int? _selectedPermDistrictId;
  int? _selectedPermPoliceStationId;

  bool _isPermSameAsCurrent = false;

  final Map<String, File?> _files = {
    'profile': null,
    'NID': null,
    'PASSPORT': null,
    'DRIVING_LICENSE': null,
    'BIRTH_CERTIFICATE': null,
  };

  final _picker = ImagePicker();

  @override
  void dispose() {
    _emailController.dispose();
    _passwordController.dispose();
    _nameController.dispose();
    _phoneController.dispose();
    _dobController.dispose();
    _holdingController.dispose();
    _areaController.dispose();
    _postalController.dispose();
    _permHoldingController.dispose();
    _permAreaController.dispose();
    _postalController.dispose();
    super.dispose();
  }

  Future<void> _pickImage(String key) async {
    final pickedFile = await _picker.pickImage(source: ImageSource.gallery);
    if (pickedFile != null) {
      setState(() => _files[key] = File(pickedFile.path));
    }
  }

  void _handleRegister() async {
    if (_formKey.currentState!.validate()) {
      final messenger = ScaffoldMessenger.of(context);
      final router = GoRouter.of(context);

      if (_selectedDob == null) {
        messenger.showSnackBar(const SnackBar(content: Text('DOB is required')));
        return;
      }
      if (_selectedPoliceStationId == null) {
        messenger.showSnackBar(const SnackBar(content: Text('Current Police Station is required')));
        return;
      }
      if (!_isPermSameAsCurrent && _selectedPermPoliceStationId == null) {
        messenger.showSnackBar(const SnackBar(content: Text('Permanent Police Station is required')));
        return;
      }

      final currentAddress = AddressRequest(
        holdingNo: _holdingController.text,
        area: _areaController.text,
        postalCode: _postalController.text,
        addressType: AddressType.PRESENT,
        policeStationId: _selectedPoliceStationId!,
      );

      final permanentAddress = _isPermSameAsCurrent
          ? currentAddress.copyWith(addressType: AddressType.PERMANENT)
          : AddressRequest(
              holdingNo: _permHoldingController.text,
              area: _permAreaController.text,
              postalCode: _permPostalController.text,
              addressType: AddressType.PERMANENT,
              policeStationId: _selectedPermPoliceStationId!,
            );

      final request = CustomerRequest(
        email: _emailController.text.trim(),
        password: _passwordController.text,
        name: _nameController.text,
        gender: _selectedGender,
        phone: _phoneController.text,
        occupation: _selectedOccupation,
        dob: _selectedDob,
        addresses: [currentAddress, permanentAddress],
      );

      final Map<String, List<int>> fileBytes = {};
      for (var entry in _files.entries) {
        if (entry.value != null) {
          fileBytes[entry.key] = await entry.value!.readAsBytes();
        }
      }

      await ref.read(authProvider.notifier).register(request, fileBytes);
      
      if (!mounted) return;

      if (!ref.read(authProvider).hasError) {
        messenger.showSnackBar(const SnackBar(content: Text('Registration successful! Please login.')));
        router.pop();
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    final authState = ref.watch(authProvider);
    final divisionsAsync = ref.watch(divisionsProvider);

    return Scaffold(
      appBar: AppBar(title: const Text('Register'), backgroundColor: Colors.transparent),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(24),
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              NeonTextField(label: 'Full Name', controller: _nameController),
              const SizedBox(height: 20),
              NeonTextField(label: 'Email', controller: _emailController, keyboardType: TextInputType.emailAddress),
              const SizedBox(height: 20),
              NeonTextField(label: 'Password', controller: _passwordController, isPassword: true),
              const SizedBox(height: 20),
              NeonTextField(label: 'Phone', controller: _phoneController, keyboardType: TextInputType.phone),
              const SizedBox(height: 20),
              
              // DOB Picker
              GestureDetector(
                onTap: () async {
                  final date = await showDatePicker(
                    context: context,
                    initialDate: DateTime.now().subtract(const Duration(days: 6570)),
                    firstDate: DateTime(1900),
                    lastDate: DateTime.now(),
                  );
                  if (date != null) {
                    setState(() {
                      _selectedDob = date;
                      _dobController.text = DateFormat('yyyy-MM-dd').format(date);
                    });
                  }
                },
                child: AbsorbPointer(
                  child: NeonTextField(label: 'Date of Birth', controller: _dobController),
                ),
              ),
              const SizedBox(height: 20),

              DropdownButtonFormField<Gender>(
                initialValue: _selectedGender,
                items: Gender.values.map((g) => DropdownMenuItem(value: g, child: Text(g.name))).toList(),
                onChanged: (v) => setState(() => _selectedGender = v!),
                decoration: const InputDecoration(labelText: 'Gender'),
              ),
              const SizedBox(height: 20),

              DropdownButtonFormField<CustomerOccupation>(
                initialValue: _selectedOccupation,
                items: CustomerOccupation.values.map((o) => DropdownMenuItem(value: o, child: Text(o.name))).toList(),
                onChanged: (v) => setState(() => _selectedOccupation = v!),
                decoration: const InputDecoration(labelText: 'Occupation'),
              ),
              const SizedBox(height: 20),

              const Text('Current Address', style: TextStyle(fontWeight: FontWeight.bold, fontSize: 18, color: AppColors.neonCyan)),
              const SizedBox(height: 10),
              NeonTextField(label: 'Holding No', controller: _holdingController),
              const SizedBox(height: 10),
              NeonTextField(label: 'Area', controller: _areaController),
              const SizedBox(height: 10),
              NeonTextField(label: 'Postal Code', controller: _postalController),
              const SizedBox(height: 10),

              // Division Dropdown
              divisionsAsync.when(
                data: (divisions) => DropdownButtonFormField<int>(
                  initialValue: _selectedDivisionId,
                  hint: const Text('Select Division'),
                  items: divisions.map((d) => DropdownMenuItem(value: d.id, child: Text(d.name ?? ''))).toList(),
                  onChanged: (v) => setState(() {
                    _selectedDivisionId = v;
                    _selectedDistrictId = null;
                    _selectedPoliceStationId = null;
                  }),
                ),
                loading: () => const LinearProgressIndicator(),
                error: (e, s) => Text('Error loading divisions: $e'),
              ),
              const SizedBox(height: 10),

              // District Dropdown
              if (_selectedDivisionId != null)
                ref.watch(districtsProvider(_selectedDivisionId!)).when(
                  data: (districts) => DropdownButtonFormField<int>(
                    initialValue: _selectedDistrictId,
                    hint: const Text('Select District'),
                    items: districts.map((d) => DropdownMenuItem(value: d.id, child: Text(d.name ?? ''))).toList(),
                    onChanged: (v) => setState(() {
                      _selectedDistrictId = v;
                      _selectedPoliceStationId = null;
                    }),
                  ),
                  loading: () => const LinearProgressIndicator(),
                  error: (e, s) => Text('Error loading districts: $e'),
                ),
              const SizedBox(height: 10),

              // Police Station Dropdown
              if (_selectedDistrictId != null)
                ref.watch(policeStationsProvider(_selectedDistrictId!)).when(
                  data: (stations) => DropdownButtonFormField<int>(
                    initialValue: _selectedPoliceStationId,
                    hint: const Text('Select Police Station'),
                    items: stations.map((s) => DropdownMenuItem(value: s.id, child: Text(s.name ?? ''))).toList(),
                    onChanged: (v) => setState(() => _selectedPoliceStationId = v),
                  ),
                  loading: () => const LinearProgressIndicator(),
                  error: (e, s) => Text('Error loading stations: $e'),
                ),

              const SizedBox(height: 32),
              Row(
                children: [
                  Checkbox(
                    value: _isPermSameAsCurrent,
                    onChanged: (v) => setState(() => _isPermSameAsCurrent = v ?? false),
                    activeColor: AppColors.neonCyan,
                    checkColor: Colors.black,
                  ),
                  const Text('Permanent address same as current', style: TextStyle(color: AppColors.textSecondary)),
                ],
              ),

              if (!_isPermSameAsCurrent) ...[
                const SizedBox(height: 20),
                const Text('Permanent Address', style: TextStyle(fontWeight: FontWeight.bold, fontSize: 18, color: AppColors.neonCyan)),
                const SizedBox(height: 10),
                NeonTextField(label: 'Holding No', controller: _permHoldingController),
                const SizedBox(height: 10),
                NeonTextField(label: 'Area', controller: _permAreaController),
                const SizedBox(height: 10),
                NeonTextField(label: 'Postal Code', controller: _permPostalController),
                const SizedBox(height: 10),

                // Permanent Division Dropdown
                divisionsAsync.when(
                  data: (divisions) => DropdownButtonFormField<int>(
                    initialValue: _selectedPermDivisionId,
                    hint: const Text('Select Division'),
                    items: divisions.map((d) => DropdownMenuItem(value: d.id, child: Text(d.name ?? ''))).toList(),
                    onChanged: (v) => setState(() {
                      _selectedPermDivisionId = v;
                      _selectedPermDistrictId = null;
                      _selectedPermPoliceStationId = null;
                    }),
                  ),
                  loading: () => const LinearProgressIndicator(),
                  error: (e, s) => Text('Error loading divisions: $e'),
                ),
                const SizedBox(height: 10),

                // Permanent District Dropdown
                if (_selectedPermDivisionId != null)
                  ref.watch(districtsProvider(_selectedPermDivisionId!)).when(
                    data: (districts) => DropdownButtonFormField<int>(
                      initialValue: _selectedPermDistrictId,
                      hint: const Text('Select District'),
                      items: districts.map((d) => DropdownMenuItem(value: d.id, child: Text(d.name ?? ''))).toList(),
                      onChanged: (v) => setState(() {
                        _selectedPermDistrictId = v;
                        _selectedPermPoliceStationId = null;
                      }),
                    ),
                    loading: () => const LinearProgressIndicator(),
                    error: (e, s) => Text('Error loading districts: $e'),
                  ),
                const SizedBox(height: 10),

                // Permanent Police Station Dropdown
                if (_selectedPermDistrictId != null)
                  ref.watch(policeStationsProvider(_selectedPermDistrictId!)).when(
                    data: (stations) => DropdownButtonFormField<int>(
                      initialValue: _selectedPermPoliceStationId,
                      hint: const Text('Select Police Station'),
                      items: stations.map((s) => DropdownMenuItem(value: s.id, child: Text(s.name ?? ''))).toList(),
                      onChanged: (v) => setState(() => _selectedPermPoliceStationId = v),
                    ),
                    loading: () => const LinearProgressIndicator(),
                    error: (e, s) => Text('Error loading stations: $e'),
                  ),
              ],

              const SizedBox(height: 30),
              const Text('Upload Documents', style: TextStyle(fontWeight: FontWeight.bold, fontSize: 18, color: AppColors.neonPink)),
              const SizedBox(height: 10),
              _DocumentPicker(label: 'Profile Picture', file: _files['profile'], onPick: () => _pickImage('profile')),
              _DocumentPicker(label: 'NID Front/Back', file: _files['NID'], onPick: () => _pickImage('NID')),
              _DocumentPicker(label: 'Passport (Optional)', file: _files['PASSPORT'], onPick: () => _pickImage('PASSPORT')),
              
              const SizedBox(height: 40),
              NeonButton(text: 'REGISTER', isLoading: authState.isLoading, onPressed: _handleRegister),
            ],
          ),
        ),
      ),
    );
  }
}

class _DocumentPicker extends StatelessWidget {
  final String label;
  final File? file;
  final VoidCallback onPick;

  const _DocumentPicker({required this.label, this.file, required this.onPick});

  @override
  Widget build(BuildContext context) {
    return ListTile(
      title: Text(label),
      subtitle: Text(file != null ? 'Selected' : 'Not selected'),
      trailing: IconButton(icon: const Icon(Icons.camera_alt, color: AppColors.neonCyan), onPressed: onPick),
    );
  }
}
