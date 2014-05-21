package com.sinosoft.one.monitor.os.linux.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QOsStati is a Querydsl query type for OsStati
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QOsStati extends EntityPathBase<OsStati> {

    private static final long serialVersionUID = -1822172892;

    public static final QOsStati osStati = new QOsStati("osStati");

    public final StringPath averageValue = createString("averageValue");

    public final StringPath id = createString("id");

    public final StringPath leastValue = createString("leastValue");

    public final StringPath maxValue = createString("maxValue");

    public final StringPath osid = createString("osid");

    public final DateTimePath<java.util.Date> statiTime = createDateTime("statiTime", java.util.Date.class);

    public final StringPath type = createString("type");

    public QOsStati(String variable) {
        super(OsStati.class, forVariable(variable));
    }

    public QOsStati(Path<? extends OsStati> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QOsStati(PathMetadata<?> metadata) {
        super(OsStati.class, metadata);
    }

}

