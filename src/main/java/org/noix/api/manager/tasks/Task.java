package org.noix.api.manager.tasks;


import org.noix.api.manager.users.User;

import java.util.Date;

public interface Task {

    String getName();

    String getDescription();

    User getOwner();

    Date getCreated();

}
