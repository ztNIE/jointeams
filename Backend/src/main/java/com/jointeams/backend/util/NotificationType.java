package com.jointeams.backend.util;

import java.util.Arrays;

public enum NotificationType {
    Invitation(0, "Invitation", new String[]{"xx", "xx"}),
    JoinRequest(1, "Join Request", new String[]{"xx", "xx"}),
    AcceptedJoinRequest(2, "Accepted Join Request", new String[]{"xx", "xx"}),
    DeclinedJoinRequest(3, "Declined Join Request", new String[]{"xx", "xx"}),
    AcceptedInvitation(4, "Accepted Invitation", new String[]{"xx", "xx"}),
    DeclinedInvitation(5, "Declined Invitation", new String[]{"xx", "xx"});

    private int code;
    private String content;
    private String[] message;

    NotificationType(int code, String content, String[] message)
    {
        this.code = code;
        this.content = content;
        this.message = message;
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
                        messageResult = searchResult.message[0] + " " + userName + " " + searchResult.message[1] + groupName;
                        break;
                    case 1:
                        messageResult = searchResult.message[0] + " " + userName + " " + searchResult.message[1] + groupName;
                        break;
                    case 2:
                        messageResult = searchResult.message[0] + " " + userName + " " + searchResult.message[1] + groupName;
                        break;
                    case 3:
                        messageResult = searchResult.message[0] + " " + userName + " " + searchResult.message[1] + groupName;
                        break;
                    case 4:
                        messageResult = searchResult.message[0] + " " + userName + " " + searchResult.message[1] + groupName;
                        break;
                    case 5:
                        messageResult = searchResult.message[0] + " " + userName + " " + searchResult.message[1] + groupName;
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
