package com.sinosoft.one.monitor.attribute.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QAttribute is a Querydsl query type for Attribute
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAttribute extends EntityPathBase<Attribute> {

    private static final long serialVersionUID = -839344000;

    public static final QAttribute attribute1 = new QAttribute("attribute1");

    public final StringPath attribute = createString("attribute");

    public final StringPath id = createString("id");

    public final StringPath resourceType = createString("resourceType");

    public final StringPath units = createString("units");

    public QAttribute(String variable) {
        super(Attribute.class, forVariable(variable));
    }

    public QAttribute(Path<? extends Attribute> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QAttribute(PathMetadata<?> metadata) {
        super(Attribute.class, metadata);
    }

}

