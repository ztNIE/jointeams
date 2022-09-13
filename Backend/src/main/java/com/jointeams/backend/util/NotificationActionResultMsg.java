package com.jointeams.backend.util;

public enum NotificationActionResultMsg {
    InvitationAccepted {
        @Override
        public String toString() {
            return "The invitation has been accepted.";
        }
    },
    InvitationDeclined {
        @Override
        public String toString() {
            return "The invitation has been declined.";
        }
    },
    InvitationDelete {
        @Override
        public String toString() {
            return "The invitation has been deleted.";
        }
    },
    JoinRequestAccepted {
        @Override
        public String toString() {
            return "The join request has been accepted.";
        }
    },
    JoinRequestDeclined {
        @Override
        public String toString() {
            return "The join request has been declined.";
        }
    },
    JoinRequestDelete {
        @Override
        public String toString() {
            return "The join request has been deleted.";
        }
    },
    NotificationDeleted {
        @Override
        public String toString() {
            return "The notification has been deleted.";
        }
    },
    InvalidedAction {
        @Override
        public String toString() {
            return "Invalided action!";
        }
    },
    ResponseLimitation {
        @Override
        public String toString() {
            return "The response cannot be accepted or declined!";
        }
    },
    AcceptingInvitationFailedCozAnotherGroup {
        @Override
        public String toString() {
            return "Accepting the invitation failed because you are already in another group of the same course and semester.";
        }
    },
    AcceptingInvitationFailedCozGroupFull {
        @Override
        public String toString() {
            return "Accepting the invitation failed because the group is full.";
        }
    },
    AcceptingJoinRequestFailedCozAnotherGroup {
        @Override
        public String toString() {
            return "Accepting the join request failed because he/she is already in another group of the same course and semester.";
        }
    },
    AcceptingJoinRequestFailedCozGroupFull {
        @Override
        public String toString() {
            return "Accepting the join request failed because the group is full.";
        }
    },
    InvalidedNotificationType {
        @Override
        public String toString() {
            return "Invalided notification type!";
        }
    },

}
