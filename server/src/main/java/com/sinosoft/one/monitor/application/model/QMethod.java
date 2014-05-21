package com.sinosoft.one.monitor.application.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QMethod is a Querydsl query type for Method
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QMethod extends EntityPathBase<Method> {

    private static final long serialVersionUID = -554868544;

    public static final QMethod method = new QMethod("method");

    public final StringPath className = createString("className");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final StringPath creatorId = createString("creatorId");

    public final StringPath description = createString("description");

    public final StringPath id = createString("id");

    public final StringPath methodName = createString("methodName");

    public final StringPath modifierId = createString("modifierId");

    public final DateTimePath<java.util.Date> modifyTime = createDateTime("modifyTime", java.util.Date.class);

    public final StringPath status = createString("status");

    public final NumberPath<Integer> threshold = createNumber("threshold", Integer.class);

    public QMethod(String variable) {
        super(Method.class, forVariable(variable));
    }

    public QMethod(Path<? extends Method> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QMethod(PathMetadata<?> metadata) {
        super(Method.class, metadata);
    }

}

