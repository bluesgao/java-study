# 工程简介



# 延伸阅读


{"indexName":"class_student","matchMap":{"school":"华师一附小"}}
{"indexName":"class_student","nestedMap":{"students":{"name":["科比","詹姆斯"]}}}

GET class_student/_search
{
  "from": 0,
  "size": 10,
  "timeout": "60s",
  "query": {
    "bool": {
      "must": [
        {
          "nested": {
            "query": {
              "bool": {
                "must": [
                  {
                    "terms": {
                      "students.name": [
                        "程咬金","科比"
                      ]
                    }
                  }
                ]
              }
            },
            "path": "students"
          }
        }
      ]
    }
  }
}


{"indexName":"class_student","nestedMap":{"students":{"matchMap":{"name":"科比"}}}} 
{"indexName":"class_student","nestedMap":{"students":{"matchMap":{"name":"科比"}}}} 


