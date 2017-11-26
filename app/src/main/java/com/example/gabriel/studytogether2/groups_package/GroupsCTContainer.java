package com.example.gabriel.studytogether2.groups_package;

/**
 * Created by Charley on 11/25/17.
 */

public class GroupsCTContainer {

    private static GroupsCTContainer gctc;

    GroupsCommonTime.CommonTimeCard ctc;


    public static GroupsCTContainer getInstance() {
        if (gctc == null)
            gctc = new GroupsCTContainer();

        return gctc;
    }

    public void setTimeCard(GroupsCommonTime.CommonTimeCard ctc) {
        this.ctc = ctc;
    }

    public GroupsCommonTime.CommonTimeCard getTimeCard() {
        return ctc;
    }
}
