package com.sinosoft.one.monitor.action.model;

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.StringPath;

import javax.annotation.Generated;

import static com.mysema.query.types.PathMetadataFactory.forVariable;


/**
 * QMailAction is a Querydsl query type for MailAction
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QMailAction extends EntityPathBase<MailAction> {

    private static final long serialVersionUID = -1790337578;

    public static final QMailAction emailAction = new QMailAction("emailAction");

    public final StringPath appendMessage = createString("appendMessage");

    public final StringPath desc = createString("desc");

    public final StringPath fromAddress = createString("fromAddress");

    public final StringPath id = createString("id");

    public final StringPath mailFormat = createString("mailFormat");

    public final StringPath smtpPort = createString("smtpPort");

    public final StringPath smtpServer = createString("smtpServer");

    public final StringPath subject = createString("subject");

    public final StringPath toAddress = createString("toAddress");

    public final StringPath name = createString("name");

    public QMailAction(String variable) {
        super(MailAction.class, forVariable(variable));
    }

    public QMailAction(Path<? extends MailAction> entity) {
        super(entity.getType(), entity.getMetadata());
    }

    public QMailAction(PathMetadata<?> metadata) {
        super(MailAction.class, metadata);
    }

}

