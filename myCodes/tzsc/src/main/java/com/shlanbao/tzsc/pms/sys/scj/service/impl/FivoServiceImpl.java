package com.shlanbao.tzsc.pms.sys.scj.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.shlanbao.tzsc.base.dao.SysFavoriteDaoI;
import com.shlanbao.tzsc.base.dao.SysResourceDaoI;
import com.shlanbao.tzsc.base.mapping.SysFavorite;
import com.shlanbao.tzsc.base.mapping.SysResource;
import com.shlanbao.tzsc.base.mapping.SysUser;
import com.shlanbao.tzsc.pms.sys.scj.service.FivoServiceI;

@Service
public class FivoServiceImpl implements FivoServiceI {
	@Autowired
	private SysFavoriteDaoI sysFavoriteDaoI;
	@Autowired
	private SysResourceDaoI resourceDaoI;

	@Override
	public boolean saveFaverite(String uid, String rid) {
		boolean Result = false;
		List<String> strs = this.getAllRidUid(rid);
		try {
			for (String str : strs) {
				// 查询是否已添加
				boolean bool = this.getByrId(str, uid);
				if (bool) {
					sysFavoriteDaoI.save(new SysFavorite(new SysResource(str),
							new SysUser(uid)));
				} else {
					continue;
				}
			}
			Result = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result;
	}

	@Override
	public boolean deleteFaverite(String rid, String uid) {
		boolean result = false;
		// 查询要删除的是不是父节点
		List<String> ids = getdelPRes(rid);
		for (String prid : ids) {
			try {
				sysFavoriteDaoI
						.deleteByParams(
								"delete SysFavorite where sysResource.id=? and sysUser.id=?",
								prid, uid);
				result = true;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return result;
	}

	private List<String> getdelPRes(String rid) {
		List<String> rids = new ArrayList<String>();
		SysResource resource = resourceDaoI.findById(SysResource.class, rid);
		rids.add(resource.getId());
		// 判断父节点下是否就这一个如果是则加上父节点一起删
		if (resource.getSysResources().size() > 0) {
			Set<SysResource> subs = resource.getSysResources();
			for (SysResource sysResource : subs) {
				rids.add(sysResource.getId());
			}
		}
		//判断选中节点下的所有子节点
		getSubs(rids, resource);
		return rids;
	}

	@Override
	public boolean getByrId(String rid, String uid) {
		List<SysFavorite> sysfavorites = sysFavoriteDaoI
				.query("from SysFavorite f where f.sysResource.id='" + rid
						+ "' and f.sysUser.id='" + uid + "'");
		if (sysfavorites == null) {
			return true;

		}
		if (sysfavorites.size() > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public List<String> getAllRidUid(String rid) {
		List<String> rids = new ArrayList<String>();
		// 1.得到父级集合（typ=1）
		SysResource resource = resourceDaoI.findById(SysResource.class, rid);
		rids.add(resource.getId());
		// 父节点
		getPRes(rids, resource);
		// 子节点
		getSubs(rids, resource);

		System.out.println(rids);
		return rids;
	}

	private void getSubs(List<String> rids, SysResource resource) {
		if (resource.getSysResources() != null
				&& resource.getSysResources().size() > 0) {
			Set<SysResource> subs = resource.getSysResources();
			for (SysResource sysResource : subs) {
				if (sysResource.getDel() == 0 && sysResource.getEnable() == 1
						&& sysResource.getTyp() == 1) {
					rids.add(sysResource.getId());
					this.getSubs(rids, sysResource);
				}
				this.getSubs(rids, sysResource);
			}
		}
	}

	private void getPRes(List<String> rids, SysResource resource) {
		if (resource.getSysResource() != null) {
			SysResource pResource = resource.getSysResource();
			rids.add(pResource.getId());
			this.getPRes(rids, pResource);
		}
	}

}
