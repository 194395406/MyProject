package com.person.srb.core.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.person.common.result.R;
import com.person.srb.core.pojo.entity.BorrowInfo;
import com.person.srb.core.pojo.entity.Borrower;
import com.person.srb.core.pojo.vo.BorrowInfoApprovalVO;
import com.person.srb.core.pojo.vo.BorrowerApprovalVO;
import com.person.srb.core.pojo.vo.BorrowerDetailVO;
import com.person.srb.core.service.BorrowInfoService;
import com.person.srb.core.service.BorrowerService;
import com.person.srb.core.service.impl.BorrowInfoServiceImpl;
import com.person.srb.core.service.impl.BorrowerServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "借款人管理")
@RestController
@RequestMapping("/admin/core/borrower")
@Slf4j
public class AdminBorrowerController {

    @Resource
    private BorrowerService borrowerService;

    @ApiOperation("获取借款人分页列表")
    @GetMapping("/list/{page}/{limit}")
    public R listPage(
            @ApiParam(value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(value = "查询关键字", required = false)
            @RequestParam String keyword) {
        //这里的@RequestParam其实是可以省略的，但是在目前的swagger版本中（2.9.2）不能省略，
        //否则默认将没有注解的参数解析为body中的传递的数据

        Page<Borrower> pageParam = new Page<>(page, limit);
        IPage<Borrower> pageModel = borrowerService.listPage(pageParam, keyword);
        return R.ok().data("pageModel", pageModel);
    }

    @ApiOperation("获取借款人信息")
    @GetMapping("/show/{id}")
    public R show(
            @ApiParam(value = "借款人id", required = true)
            @PathVariable Long id) {
        BorrowerDetailVO borrowerDetailVO = borrowerService.getBorrowerDetailVOById(id);
        return R.ok().data("borrowerDetailVO", borrowerDetailVO);
    }

//    @ApiOperation("借款额度审批")
//    @PostMapping("/approvalValue")
//    public R approval(@RequestBody BorrowerApprovalVO borrowerApprovalVO) {
//        borrowerService.approval(borrowerApprovalVO);
//        return R.ok().message("审批完成");
//    }

    @Resource
    private BorrowInfoService borrowInfoService;

    @ApiOperation("借款信息列表")
    @GetMapping("/list")
    public R list() {
        List<BorrowInfo> borrowInfoList = borrowInfoService.selectList();
        log.info("获得的信息是"+borrowInfoList.isEmpty());
        return R.ok().data("list", borrowInfoList);
    }

    @ApiOperation("获取借款信息")
    @GetMapping("/showBorrow/{id}")
    public R showBorrow(
            @ApiParam(value = "借款id", required = true)
            @PathVariable Long id) {
        Map<String, Object> borrowInfoDetail = borrowInfoService.getBorrowInfoDetail(id);
        return R.ok().data("borrowInfoDetail", borrowInfoDetail);
    }

    @Resource
    private BorrowerServiceImpl borrowerServiceImpl;

    @ApiOperation("审批借款信息")
    @PostMapping("/approval")
    public R approval(@RequestBody BorrowerApprovalVO borrowerApprovalVO) {
        if (borrowerApprovalVO == null){

            log.info("审批借款信息的数据为空");
        }else {
            log.info("审批借款信息的数据不为空");

        }

        borrowerServiceImpl.approval(borrowerApprovalVO);
        return R.ok().message("审批完成");
    }
}
