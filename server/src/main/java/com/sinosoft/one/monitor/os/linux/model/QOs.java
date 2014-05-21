package com.sinosoft.one.monitor.os.linux.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QOs is a Querydsl query type for Os
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QOs extends EntityPathBase<Os> {

    private static final long serialVersionUID = 724666865;

    public static final QOs os = new QOs("os");

    public final StringPath intercycleTime = createString("intercycleTime");

    public final StringPath ipAddr = createString("ipAddr");

    public final StringPath name = createString("name");

    public final ListPath<OsAvailable, QOsAvailabile> osAvailabiles = this.<OsAvailable, QOsAvailabile>createList("osAvailabiles", OsAvailable.class, QOsAvailabile.class);

    public final ListPath<OsAvailabletemp, QOsAvailabletemp> osAvailabletemps = this.<OsAvailabletemp, QOsAvailabletemp>createList("osAvailabletemps", OsAvailabletemp.class, QOsAvailabletemp.class);

    public final ListPath<OsCpu, QOsCpu> osCpus = this.<OsCpu, QOsCpu>createList("osCpus", OsCpu.class, QOsCpu.class);

    public final ListPath<OsDisk, QOsDisk> osDisks = this.<OsDisk, QOsDisk>createList("osDisks", OsDisk.class, QOsDisk.class);

    public final StringPath osInfoId = createString("osInfoId");

    public final ListPath<OsRam, QOsRam> osRams = this.<OsRam, QOsRam>createList("osRams", OsRam.class, QOsRam.class);

    public final StringPath subnetMask = createString("subnetMask");

    public final StringPath type = createString("type");

    public QOs(String variable) {
        super(Os.class, forVariable(variable));
    }

    public QOs(Path<? extends Os> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QOs(PathMetadata<?> metadata) {
        super(Os.class, metadata);
    }

}

