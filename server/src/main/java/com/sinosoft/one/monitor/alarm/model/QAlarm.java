package com.sinosoft.one.monitor.alarm.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QAlarm is a Querydsl query type for Alarm
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAlarm extends EntityPathBase<Alarm> {

    private static final long serialVersionUID = 1330184757;

    public static final QAlarm alarm = new QAlarm("alarm");

    public final StringPath alarmFrom = createString("alarmFrom");

    public final StringPath attributeId = createString("attributeId");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final StringPath id = createString("id");

    public final StringPath message = createString("message");

    public final StringPath monitorId = createString("monitorId");

    public final StringPath monitorType = createString("monitorType");

    public final StringPath ownerName = createString("ownerName");

    public final StringPath severity = createString("severity");

    public QAlarm(String variable) {
        super(Alarm.class, forVariable(variable));
    }

    public QAlarm(Path<? extends Alarm> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QAlarm(PathMetadata<?> metadata) {
        super(Alarm.class, metadata);
    }

}

