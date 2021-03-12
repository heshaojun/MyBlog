package cn.codejavahand.service

import cn.codejavahand.bo.ArticleDetailBo
import cn.codejavahand.common.RestResp
import cn.codejavahand.dao.po.ArticleDetailPo
import org.springframework.stereotype.Service

/**
 * @author heshaojun* @date 2021/3/10
 * @description TODO
 */
@Service
class ArticleDetailService {
    RestResp doService(String articleId) {
        tempData()
    }


    private RestResp tempData() {
        [
                code: 200,
                msg : "ok",
                data: [
                        title  : "文章标题",
                        summery: "文章概要",
                        type   : "文章类型",
                        time   : "2020-10-30 10:44:23",
                        visit  : "10万+",
                        comment: "100",
                        id     : "dfdsfjsdl",
                        context: "<!--- SPDX-License-Identifier: Apache-2.0 -->\n\n<p align=\"center\"><img width=\"40%\" src=\"docs/ONNX_logo_main.png\" /></p>\n\n[![Build Status](https://img.shields.io/azure-devops/build/onnx-pipelines/onnx/7?label=Linux&logo=Azure-Pipelines)](https://dev.azure.com/onnx-pipelines/onnx/_build/latest?definitionId=7&branchName=master)\n[![Build Status](https://img.shields.io/azure-devops/build/onnx-pipelines/onnx/5?label=Windows&logo=Azure-Pipelines)](https://dev.azure.com/onnx-pipelines/onnx/_build/latest?definitionId=5&branchName=master)\n[![Build Status](https://img.shields.io/azure-devops/build/onnx-pipelines/onnx/6?label=MacOS&logo=Azure-Pipelines)](https://dev.azure.com/onnx-pipelines/onnx\n# Contribute\nONNX is a [community project](community). ",
                        pre    : [
                                title: "dsfdsf",
                                id   : "sdfdsfads",
                        ] as ArticleDetailPo.Child,
                        next   : null,
                ] as ArticleDetailBo
        ] as RestResp
    }

}
