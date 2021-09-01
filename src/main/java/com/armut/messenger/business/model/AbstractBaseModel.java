package com.armut.messenger.business.model;

import com.armut.messenger.business.constant.ProjectConstants;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @CreationTimestamp
    @Column(name = ProjectConstants.CREATION_DATE_COLUMN_NAME, updatable = false)
    private LocalDateTime creationDate;

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
