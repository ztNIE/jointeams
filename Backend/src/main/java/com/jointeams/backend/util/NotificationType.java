package com.jointeams.backend.util;

import java.util.Arrays;

public enum NotificationType {
    Invitation(0, "Invitation"),
    JoinRequest(1, "Join Request"),
    AcceptedJoinRequest(2, "Accepted Join Request"),
    DeclinedJoinRequest(3, "Declined Join Request"),
    AcceptedInvitation(4, "Accepted Invitation"),
    DeclinedInvitation(5, "Declined Invitation");

    private int code;
    private String content;

    NotificationType(int code, String content)
    {
        this.code = code;
        this.content = content;
    }

    public static String getContentByCode(int code) {
        NotificationType searchResult = Arrays.stream(NotificationType.values()).filter(value ->code == value.code )
                                        .findFirst().orElse(null);
        if(searchResult != null)
            return searchResult.content;
        else
            return "Error notification code!";
    }

    public static String getMessageByCode(int code, String userName, String groupName) {
        NotificationType searchResult = Arrays.stream(NotificationType.values()).filter(value ->code == value.code )
                .findFirst().orElse(null);
        if(searchResult != null)
        {
            String messageResult = "";
            try {
                switch(code)
                {
                    case 0:
                        messageResult = "The Group " + groupName + "  invites you to join there group. For more information, please go to their group page.";
                        break;
                    case 1:
                        messageResult = userName + " applied to join the Group " + groupName + ".  For more information, please go to the applicant's profile.";
                        break;
                    case 2:
                        messageResult = groupName + " has accepted your join request. For more information, please go to the group page.";
                        break;
                    case 3:
                        messageResult = groupName + " has declined your join request.";
                        break;
                    case 4:
                        messageResult = userName + " has accepted the invitation from Group " + groupName + ".For more information, please go to the group page.";
                        break;
                    case 5:
                        messageResult = userName + " has declined the invitation from Group " + groupName + ".";
                        break;
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            return messageResult;
        }
        else
            return "Error notification code!";
    }
}
