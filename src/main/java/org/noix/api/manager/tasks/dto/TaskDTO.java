package org.noix.api.manager.tasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private Long id;

    private String name;
    private String description;

    //no owner
    private Date created;
    private Date updated;

    public boolean isNameValid() {
        if (name == null) return false;
        return name.length() >= 6 &&
                name.length() <= 30;
    }
}
