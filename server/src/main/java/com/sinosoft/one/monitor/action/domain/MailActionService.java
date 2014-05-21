package com.sinosoft.one.monitor.action.domain;

import java.util.List;

import com.sinosoft.one.monitor.action.model.MailAction;
import com.sinosoft.one.monitor.action.repository.MailActionRepository;
import com.sinosoft.one.monitor.attribute.repository.AttributeActionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class MailActionService {

	private static final Logger logger = LoggerFactory
			.getLogger(MailActionService.class);
	private MailActionRepository mailActionRepository;
	
	private AttributeActionRepository attributeActionRepository;
	
	public void saveEntity(MailAction entity) {
		if (logger.isDebugEnabled()) {
			logger.info("ActionService$saveEntity开始执行，参数entity={}", entity);
		}
		Assert.notNull(entity, "ActionService$saveEntity中的参数entity不能为NULL.");
		mailActionRepository.save(entity);
	}

/*	public void deleteEntity(MailAction entity) {
		if (logger.isDebugEnabled()) {
			logger.info("ActionService$deleteEntity开始执行，参数entity={}", entity);
		}
		Assert.notNull(entity, "ActionService$deleteEntity中的参数entity不能为NULL.");
		mailActionRepository.delete(entity);
		attributeActionRepository.deleteByActionId(entity.getId());
	}*/

	public void deleteEntity(String id) {
		if (logger.isDebugEnabled()) {
			logger.info("ActionService$deleteEntity开始执行，参数id={}", id);
		}
		Assert.notNull(id, "ActionService$deleteEntity中的参数id不能为NULL.");
		mailActionRepository.delete(id);
		attributeActionRepository.deleteByActionId(id);
	}

	/*public void deleteEntities(String ids) {
		if (logger.isDebugEnabled()) {
			logger.info("ActionService$deleteEntities开始执行，参数ids={}", ids);
		}
		if("".equals(ids)){
			logger.info("ActionService$deleteEntities的参数ids={}不能为空.", ids);
		}
		Assert.notNull(ids, "ActionService$deleteEntities中的参数ids不能为NULL.");
		mailActionRepository.deleteByIDs(ids);
	}*/

	public MailAction getMailAction(String id) {
		if (logger.isDebugEnabled()) {
			logger.info("ActionService$getMailAction开始执行，参数id={}", id);
		}
		Assert.notNull(id, "ActionService$getMailAction中的参数id不能为NULL.");
		return mailActionRepository.findOne(id);
	}

	public List<MailAction> getAllMailAction() {
		return this.getAllMailAction(null);
	}

	public List<MailAction> getAllMailAction(Sort sort) {
		if (sort == null) {
			sort = new Sort(Direction.ASC, "id");
		}
		if (logger.isDebugEnabled()) {
			logger.info("ActionService$getAllEmailAction开始执行，参数sort={}", sort);
		}
		return (List<MailAction>) mailActionRepository.findAll(sort);
	}

	@Autowired
	public void setMailActionRepository(MailActionRepository mailActionRepository) {
		this.mailActionRepository = mailActionRepository;
	}
}
