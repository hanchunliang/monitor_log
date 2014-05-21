package com.sinosoft.one.monitor.application.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QEumUrlAvaSta is a Querydsl query type for EumUrlAvaSta
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QEumUrlAvaSta extends EntityPathBase<EumUrlAvaSta> {

    private static final long serialVersionUID = -1322339230;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QEumUrlAvaSta eumUrlAvaSta = new QEumUrlAvaSta("eumUrlAvaSta");

    public final NumberPath<java.math.BigDecimal> avgFailureTime = createNumber("avgFailureTime", java.math.BigDecimal.class);

    public final QEumUrl eumUrl;

    public final NumberPath<java.math.BigDecimal> failureCount = createNumber("failureCount", java.math.BigDecimal.class);

    public final StringPath id = createString("id");

    public final NumberPath<java.math.BigDecimal> normalRuntime = createNumber("normalRuntime", java.math.BigDecimal.class);

    public final DateTimePath<java.util.Date> recordTime = createDateTime("recordTime", java.util.Date.class);

    public final NumberPath<java.math.BigDecimal> totalFailureTime = createNumber("totalFailureTime", java.math.BigDecimal.class);

    public QEumUrlAvaSta(String variable) {
        this(EumUrlAvaSta.class, forVariable(variable), INITS);
    }

    public QEumUrlAvaSta(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QEumUrlAvaSta(PathMetadata<?> metadata, PathInits inits) {
        this(EumUrlAvaSta.class, metadata, inits);
    }

    public QEumUrlAvaSta(Class<? extends EumUrlAvaSta> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.eumUrl = inits.isInitialized("eumUrl") ? new QEumUrl(forProperty("eumUrl")) : null;
    }

}

