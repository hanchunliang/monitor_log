package com.sinosoft.one.monitor.resources.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QResource is a Querydsl query type for Resource
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QResource extends EntityPathBase<Resource> {

    private static final long serialVersionUID = 1459467273;

    public static final QResource resources = new QResource("resources");

    public final StringPath resourceId = createString("resourceId");

    public final StringPath resourceName = createString("resourceName");

    public final StringPath resourceType = createString("resourceType");

    public QResource(String variable) {
        super(Resource.class, forVariable(variable));
    }

    public QResource(Path<? extends Resource> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QResource(PathMetadata<?> metadata) {
        super(Resource.class, metadata);
    }

}

