package org.noix.api.manager.tasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {

    private String name;
    private String description;

    public boolean isNameValid() {
        if (name == null) return false;
        return name.length() >= 6 &&
                name.length() <= 30;
    }
}
