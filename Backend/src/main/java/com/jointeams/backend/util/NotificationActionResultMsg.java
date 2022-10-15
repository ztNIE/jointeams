package com.jointeams.backend.util;

import lombok.Getter;

@Getter
public enum NotificationActionResultMsg {
    InvitationAccepted(1) {
        @Override
        public String toString() {
            return "The invitation has been accepted.";
        }
    },
    InvitationDeclined(2) {
        @Override
        public String toString() {
            return "The invitation has been declined.";
        }
    },
    InvitationDelete(3) {
        @Override
        public String toString() {
            return "The invitation has been deleted.";
        }
    },
    JoinRequestAccepted(4) {
        @Override
        public String toString() {
            return "The join request has been accepted.";
        }
    },
    JoinRequestDeclined(5) {
        @Override
        public String toString() {
            return "The join request has been declined.";
        }
    },
    JoinRequestDelete(6) {
        @Override
        public String toString() {
            return "The join request has been deleted.";
        }
    },
    NotificationDeleted(7) {
        @Override
        public String toString() {
            return "The notification has been deleted.";
        }
    },
    NotificationNotFound(0){
        @Override
        public String toString() {
            return "The notification is not found.";
        }
    },
    InvalidedAction(-1) {
        @Override
        public String toString() {
            return "Invalid action!";
        }
    },
    ResponseLimitation(-2) {
        @Override
        public String toString() {
            return "The response cannot be accepted or declined!";
        }
    },
    AcceptingInvitationFailedCozAnotherGroup(-3) {
        @Override
        public String toString() {
            return "Accepting the invitation failed because you are already in a group of this course in this semester.";
        }
    },
    AcceptingInvitationFailedCozGroupFull(-4) {
        @Override
        public String toString() {
            return "Accepting the invitation failed because the group is full.";
        }
    },
    AcceptingJoinRequestFailedCozAnotherGroup(-5) {
        @Override
        public String toString() {
            return "Accepting the join request failed because he/she is already in a group of this course in this semester.";
        }
    },
    AcceptingJoinRequestFailedCozGroupFull(-6) {
        @Override
        public String toString() {
            return "Accepting the join request failed because the group is full.";
        }
    },
    InvalidedNotificationType(-7) {
        @Override
        public String toString() {
            return "Invalid notification type!";
        }
    };

    int value;

    NotificationActionResultMsg(int value) {
        this.value = value;
    }
}
