package com.t13g2.forum.commons.events.model;

import com.t13g2.forum.commons.events.BaseEvent;

//@@xllx1
/** Shows announcement pop up*/
public class ShowAnnouncementEvent extends BaseEvent {

    public final String announcementTitle;
    public final String announcementContent;

    public ShowAnnouncementEvent(String aTitle, String aContent) {
        this.announcementTitle = aTitle;
        this.announcementContent = aContent;
    }

    @Override
    public String toString() {
        return "Showing announcement";
    }
}
