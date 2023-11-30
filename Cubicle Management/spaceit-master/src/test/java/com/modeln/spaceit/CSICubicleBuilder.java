package com.modeln.spaceit;

import com.modeln.spaceit.entities.CSICubicle;
import com.modeln.spaceit.entities.CSILocation;
import com.modeln.spaceit.enums.CSICubicleStatus;
import org.junit.jupiter.api.Test;

public class CSICubicleBuilder {

    CSICubicle csiCubicle;


    CSICubicleBuilder() {
        csiCubicle = new CSICubicle();
    }

    public CSICubicleBuilder withCubicleId(String id) {
        csiCubicle.setCubicleId(id);
        return this;
    }

    public CSICubicleBuilder withStatus(CSICubicleStatus status) {
        csiCubicle.setStatus(status);
        return this;
    }

    public CSICubicleBuilder withLocation(CSILocation location) {
        csiCubicle.setLocation(location);
        return this;
    }

    public CSICubicle build() {
        return csiCubicle;
    }


}
