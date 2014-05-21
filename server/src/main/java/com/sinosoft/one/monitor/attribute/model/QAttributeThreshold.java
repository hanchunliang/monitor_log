package com.sinosoft.one.monitor.attribute.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QAttributeThreshold is a Querydsl query type for AttributeThreshold
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAttributeThreshold extends EntityPathBase<AttributeThreshold> {

    private static final long serialVersionUID = -1882721333;

    public static final QAttributeThreshold attributeThreshold = new QAttributeThreshold("attributeThreshold");

    public final StringPath attributeId = createString("attributeId");

    public final StringPath id = createString("id");

    public final StringPath resourceId = createString("resourceId");

    public final StringPath thresholdId = createString("thresholdId");

    public QAttributeThreshold(String variable) {
        super(AttributeThreshold.class, forVariable(variable));
    }

    public QAttributeThreshold(Path<? extends AttributeThreshold> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QAttributeThreshold(PathMetadata<?> metadata) {
        super(AttributeThreshold.class, metadata);
    }

}

