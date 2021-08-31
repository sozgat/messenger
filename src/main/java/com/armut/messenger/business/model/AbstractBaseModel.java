package com.armut.messenger.business.model;

import com.armut.messenger.business.constant.ProjectConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
@Data
public abstract class AbstractBaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ProjectConstants.ID_COLUMN_NAME, unique = true, nullable = false, updatable = false)
    private long id = ProjectConstants.ID_UNSAVED_VALUE;

    @Column(name = ProjectConstants.VERSION_COLUMN_NAME)
    @Version()
    private long version = ProjectConstants.ID_UNSAVED_VALUE;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = ProjectConstants.ACTIVE_COLUMN_NAME, nullable = false)
    private boolean active = true;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @CreatedDate
    @Column(name = ProjectConstants.CREATION_DATE_COLUMN_NAME, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBaseModel that = (AbstractBaseModel) o;
        return id == that.id &&
                version == that.version &&
                active == that.active &&
                Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, active, creationDate);
    }
}
