package com.myriadquest.kreiscms.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.myriadquest.kreiscms.domain.VisionAndMission} entity. This class is used
 * in {@link com.myriadquest.kreiscms.web.rest.VisionAndMissionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vision-and-missions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VisionAndMissionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter vision;

    private StringFilter mission;

    private StringFilter tenantId;

    public VisionAndMissionCriteria() {
    }

    public VisionAndMissionCriteria(VisionAndMissionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.vision = other.vision == null ? null : other.vision.copy();
        this.mission = other.mission == null ? null : other.mission.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
    }

    @Override
    public VisionAndMissionCriteria copy() {
        return new VisionAndMissionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVision() {
        return vision;
    }

    public void setVision(StringFilter vision) {
        this.vision = vision;
    }

    public StringFilter getMission() {
        return mission;
    }

    public void setMission(StringFilter mission) {
        this.mission = mission;
    }

    public StringFilter getTenantId() {
        return tenantId;
    }

    public void setTenantId(StringFilter tenantId) {
        this.tenantId = tenantId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VisionAndMissionCriteria that = (VisionAndMissionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(vision, that.vision) &&
            Objects.equals(mission, that.mission) &&
            Objects.equals(tenantId, that.tenantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        vision,
        mission,
        tenantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VisionAndMissionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (vision != null ? "vision=" + vision + ", " : "") +
                (mission != null ? "mission=" + mission + ", " : "") +
                (tenantId != null ? "tenantId=" + tenantId + ", " : "") +
            "}";
    }

}
