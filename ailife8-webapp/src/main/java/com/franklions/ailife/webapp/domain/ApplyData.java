package com.franklions.ailife.webapp.domain;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/27
 * @since Jdk 1.8
 */
public class ApplyData {
    private Long id;

    private Integer issuenumber;

    private String applycode;

    private String applyname;

    private Long ts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIssuenumber() {
        return issuenumber;
    }

    public void setIssuenumber(Integer issuenumber) {
        this.issuenumber = issuenumber;
    }

    public String getApplycode() {
        return applycode;
    }

    public void setApplycode(String applycode) {
        this.applycode = applycode == null ? null : applycode.trim();
    }

    public String getApplyname() {
        return applyname;
    }

    public void setApplyname(String applyname) {
        this.applyname = applyname == null ? null : applyname.trim();
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }
}
