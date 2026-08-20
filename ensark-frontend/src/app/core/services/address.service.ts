import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Division, District, PoliceStation } from '../models/address.models';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AddressService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;

  getDivisions(): Observable<Division[]> {
    return this.http.get<Division[]>(`${this.apiUrl}division/all`);
  }

  getDivisionById(id: number): Observable<Division> {
    return this.http.get<Division>(`${this.apiUrl}division/${id}`);
  }

  saveDivision(division: { name: string }): Observable<Division> {
    return this.http.post<Division>(`${this.apiUrl}division/`, division);
  }

  updateDivision(id: number, division: { name: string }): Observable<Division> {
    return this.http.put<Division>(`${this.apiUrl}division/${id}/update`, division);
  }

  deleteDivision(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}division/${id}/delete`, { responseType: 'text' });
  }

  getDistrictsByDivision(divisionId: number): Observable<District[]> {
    return this.http.get<District[]>(`${this.apiUrl}district/division/${divisionId}`);
  }

  getAllDistricts(): Observable<District[]> {
    return this.http.get<District[]>(`${this.apiUrl}district/all`);
  }

  saveDistrict(district: { name: string; division: { id: number } }): Observable<District> {
    return this.http.post<District>(`${this.apiUrl}district/`, district);
  }

  updateDistrict(id: number, district: { name: string; division: { id: number } }): Observable<District> {
    return this.http.put<District>(`${this.apiUrl}district/${id}`, district);
  }

  deleteDistrict(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}district/${id}/delete`, { responseType: 'text' });
  }

  getPoliceStationsByDistrict(districtId: number): Observable<PoliceStation[]> {
    return this.http.get<PoliceStation[]>(`${this.apiUrl}policestation/district/${districtId}`);
  }

  getAllPoliceStations(): Observable<PoliceStation[]> {
    return this.http.get<PoliceStation[]>(`${this.apiUrl}policestation/`);
  }

  getPoliceStationById(id: number): Observable<PoliceStation> {
    return this.http.get<PoliceStation>(`${this.apiUrl}policestation/${id}`);
  }

  savePoliceStation(policeStation: { name: string; district: { id: number } }): Observable<PoliceStation> {
    return this.http.post<PoliceStation>(`${this.apiUrl}policestation/`, policeStation);
  }

  updatePoliceStation(id: number, policeStation: { name: string; district: { id: number } }): Observable<PoliceStation> {
    return this.http.post<PoliceStation>(`${this.apiUrl}policestation/${id}`, policeStation);
  }

  deletePoliceStation(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}policestation/${id}`, { responseType: 'text' });
  }
}
