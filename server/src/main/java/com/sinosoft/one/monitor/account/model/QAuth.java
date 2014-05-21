package com.sinosoft.one.monitor.account.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QAuth is a Querydsl query type for Auth
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QAuth extends EntityPathBase<Auth> {

    private static final long serialVersionUID = -1020578230;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QAuth auth = new QAuth("auth");

    public final QAccount account;

    public final StringPath id = createString("id");

    public final StringPath role = createString("role");

    public QAuth(String variable) {
        this(Auth.class, forVariable(variable), INITS);
    }

    public QAuth(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAuth(PathMetadata<?> metadata, PathInits inits) {
        this(Auth.class, metadata, inits);
    }

    public QAuth(Class<? extends Auth> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account")) : null;
    }

}

