package com.electricalequipment.eservice.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Description entity.
 */
public class DescriptionDTO implements Serializable {

    private Long id;

    @NotNull
    private String parameter;


    private Long itemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DescriptionDTO descriptionDTO = (DescriptionDTO) o;
        if (descriptionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), descriptionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DescriptionDTO{" +
            "id=" + getId() +
            ", parameter='" + getParameter() + "'" +
            ", item=" + getItemId() +
            "}";
    }
}
