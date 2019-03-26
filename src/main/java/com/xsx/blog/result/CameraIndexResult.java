package com.xsx.blog.result;

import java.util.List;
import com.xsx.blog.vo.CameraCoverVo;

/**
 * @Description:
 * @Date: 2019-03-21 07:58
 * @Auther: xieshengxiang
 */
public class CameraIndexResult extends AbstractPageResult<CameraCoverVo> {

    private List<String> years;

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }
}
