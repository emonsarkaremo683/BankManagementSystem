package com.elitetech_inc.ensarkbank.dashboard;

import com.elitetech_inc.ensarkbank.common.enums.Role;

import java.util.List;

public interface DashboardService {
    DashboardResponse getDashboardData(List<Long> branchIds, Role role);
}
