package com.sinosoft.one.monitor.os.linux.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QOsRespondtime is a Querydsl query type for OsRespondtime
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QOsRespondtime extends EntityPathBase<OsRespondtime> {

    private static final long serialVersionUID = -1129828815;

    public static final QOsRespondtime osRespondtime = new QOsRespondtime("osRespondtime");

    public final StringPath id = createString("id");

    public final StringPath osInfoId = createString("osInfoId");

    public final StringPath respondTime = createString("respondTime");

    public final DateTimePath<java.util.Date> sampleDate = createDateTime("sampleDate", java.util.Date.class);

    public QOsRespondtime(String variable) {
        super(OsRespondtime.class, forVariable(variable));
    }

    public QOsRespondtime(Path<? extends OsRespondtime> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QOsRespondtime(PathMetadata<?> metadata) {
        super(OsRespondtime.class, metadata);
    }

}

