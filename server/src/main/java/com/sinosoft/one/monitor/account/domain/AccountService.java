package com.sinosoft.one.monitor.account.domain;


import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.one.monitor.account.model.Account;
import com.sinosoft.one.monitor.account.model.Auth;
import com.sinosoft.one.monitor.account.repository.AccountRepository;
import com.sinosoft.one.monitor.account.repository.AuthRepository;

/**
 * @author Administrator
 */
//Spring Bean的标识.
@Component
public class AccountService {

    //private static Logger logger = LoggerFactory.getLogger(AccountManager.class);
	
    private AccountRepository accountRepository;
    @Autowired
    private AuthRepository authRepository;
    @Transactional(readOnly = false)
    public void saveAccount(Account account) {
    	Auth auth=new Auth();
    	auth.setAccount(account);
    	if(account.getAuthflag().toString().equals("1")){
    		auth.setRole("user");
    	}
    	if(account.getAuthflag().toString().equals("0")){
    		auth.setRole("admin");
    	}
    
    	account=accountRepository.save(account);
    	auth.setAccount(account);
    	auth=authRepository.save(auth);
        
    }
    /**
     * 删除用户
     */
    @Transactional(readOnly = false)
    public void deleteAccount(String id) {
        accountRepository.delete(id);
    }

    /**
     * 修改用户.
     */
    @Transactional(readOnly = false)
    public void updateAccount(Account account,String role) {
    	Auth auth= account.getAuths().get(0);
    	if(role.equals("1")){
    		auth.setRole("user");
    	}
    	if(role.equals("0")){
    		auth.setRole("admin");
    	}
    	authRepository.save(auth);
        accountRepository.save(account);
    }

    public void deleteEntities(String ids){
    	accountRepository.deleteByIDs(ids);
    }
    public Account getAccount(String id) {
        return accountRepository.findOne(id);
    }

    public List<Account> getAllAccount() {
//        return (List<Account>) accountRepository.findAll(new Sort(Direction.ASC, "id"));
    	return accountRepository.findAllNotAdmin();
    }

    public Account findUserByLoginName(String loginName) {
        return accountRepository.findByLoginName(loginName);
    }
    @Autowired
	public void setaccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
    
    
}
