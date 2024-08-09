package org.noix.api.manager.entity.task;


import org.noix.api.manager.entity.User;

import java.util.Date;

public interface Task {

    String getName();

    String getDescription();

    User getOwner();

    Date getCreated();

}
