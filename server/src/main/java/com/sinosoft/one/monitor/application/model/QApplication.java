package com.sinosoft.one.monitor.application.model;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;
import java.math.BigDecimal;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QApplication is a Querydsl query type for Application
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QApplication extends EntityPathBase<Application> {

    private static final long serialVersionUID = 526813393;

    public static final QApplication application = new QApplication("application");

    public final StringPath applicationIp = createString("applicationIp");

    public final StringPath applicationName = createString("applicationName");

    public final StringPath applicationPort = createString("applicationPort");

    public final ListPath<BizScenario, QBizScenario> bizScenarios = this.<BizScenario, QBizScenario>createList("bizScenarios", BizScenario.class, QBizScenario.class);

    public final StringPath cnName = createString("cnName");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final StringPath creatorId = createString("creatorId");

    public final StringPath id = createString("id");

    public final NumberPath<BigDecimal> interval = createNumber("interval", java.math.BigDecimal.class);

    public final StringPath modifierId = createString("modifierId");

    public final DateTimePath<java.util.Date> modifyTime = createDateTime("modifyTime", java.util.Date.class);

    public final StringPath status = createString("status");

    public QApplication(String variable) {
        super(Application.class, forVariable(variable));
    }

    public QApplication(Path<? extends Application> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QApplication(PathMetadata<?> metadata) {
        super(Application.class, metadata);
    }

}

