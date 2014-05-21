package com.sinosoft.one.monitor.attribute.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QAttributeAction is a Querydsl query type for AttributeAction
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAttributeAction extends EntityPathBase<AttributeAction> {

    private static final long serialVersionUID = 1085450966;

    public static final QAttributeAction attributeAction = new QAttributeAction("attributeAction");

    public final StringPath actionId = createString("actionId");

    public final StringPath actionType = createString("actionType");

    public final StringPath attributeId = createString("attributeId");

    public final StringPath id = createString("id");

    public final StringPath resourceId = createString("resourceId");

    public final StringPath severity = createString("severity");

    public QAttributeAction(String variable) {
        super(AttributeAction.class, forVariable(variable));
    }

    public QAttributeAction(Path<? extends AttributeAction> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QAttributeAction(PathMetadata<?> metadata) {
        super(AttributeAction.class, metadata);
    }

}

