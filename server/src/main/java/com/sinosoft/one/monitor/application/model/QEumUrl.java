package com.sinosoft.one.monitor.application.model;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.*;
import com.mysema.query.types.path.*;

import javax.annotation.Generated;


/**
 * QEumUrl is a Querydsl query type for EumUrl
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QEumUrl extends EntityPathBase<EumUrl> {

    private static final long serialVersionUID = -1590787314;

    public static final QEumUrl eumUrl = new QEumUrl("eumUrl");

    public final StringPath applicationId = createString("applicationId");

    public final ListPath<EumUrlAva, QEumUrlAva> eumUrlAvas = this.<EumUrlAva, QEumUrlAva>createList("eumUrlAvas", EumUrlAva.class, QEumUrlAva.class);

    public final ListPath<EumUrlAvaSta, QEumUrlAvaSta> eumUrlAvaStas = this.<EumUrlAvaSta, QEumUrlAvaSta>createList("eumUrlAvaStas", EumUrlAvaSta.class, QEumUrlAvaSta.class);

    public final StringPath id = createString("id");

    public final DateTimePath<java.util.Date> recordTime = createDateTime("recordTime", java.util.Date.class);

    public final StringPath url = createString("url");

    public QEumUrl(String variable) {
        super(EumUrl.class, forVariable(variable));
    }

    public QEumUrl(Path<? extends EumUrl> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QEumUrl(PathMetadata<?> metadata) {
        super(EumUrl.class, metadata);
    }

}

