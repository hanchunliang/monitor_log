package com.sinosoft.one.monitor.application.model;

import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.PathInits;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QBizScenario is a Querydsl query type for BizScenario
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QBizScenario extends EntityPathBase<BizScenario> {

    private static final long serialVersionUID = 1923814724;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QBizScenario bizScenario = new QBizScenario("bizScenario");

    public final QApplication application;

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final StringPath creatorId = createString("creatorId");

    public final StringPath id = createString("id");

    public final StringPath bizScenarioGrade = createString("bizScenarioGrade");

    public final StringPath modifierId = createString("modifierId");

    public final DateTimePath<java.util.Date> modifyTime = createDateTime("modifyTime", java.util.Date.class);

    public final StringPath name = createString("name");

    public final StringPath status = createString("status");

    public QBizScenario(String variable) {
        this(BizScenario.class, forVariable(variable), INITS);
    }

    public QBizScenario(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QBizScenario(PathMetadata<?> metadata, PathInits inits) {
        this(BizScenario.class, metadata, inits);
    }

    public QBizScenario(Class<? extends BizScenario> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.application = inits.isInitialized("application") ? new QApplication(forProperty("application")) : null;
    }

}

