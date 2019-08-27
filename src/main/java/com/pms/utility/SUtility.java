package com.pms.utility;

import java.util.Date;

public class SUtility {
    public static String getWorkStatus(int status) {
        switch (status) {
            case 1:
                return "Project Approval Pending";
            case 2:
                return "Project Approved";
            case 3:
                return "Applied for Completion";
            case 4:
                return "Finished";
            default:
                return "No Activity";
        }
    }

    public static String getProjectStatus(int approvalStatus, Date startingDate) {
        switch (approvalStatus) {
            case 0:
                return "Approval Pending";
            case 1: {
                if (startingDate.after(new Date())) {
                    return "Approved But Not Started";
                } else {
                    return "Ongoing";
                }
            }
            case 2:
            default:
                return "Finished";
        }
    }
}
