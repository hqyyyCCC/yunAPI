import { PageContainer } from '@ant-design/pro-components';
import {Badge, Button, Card, Descriptions, DescriptionsProps, Divider, Form, Input, message, Spin,} from 'antd';
import React, {useEffect, useState} from 'react';
import {
  getInterfaceInfoByIdUsingGet, invokeInterfaceInfoUsingPost,

} from "@/services/yunapi-backend/interfaceInfoController";
import { useParams} from "react-router";
import {FormProps} from "antd/lib";


/**
 * 主页
 * @constructor
 */

const Index: React.FC = () => {
  /*const { token } = theme.useToken();
  const { initialState } = useModel('@@initialState');*/
  // const [initLoading, setInitLoading] = useState(true);
  const [loading, setLoading] = useState(false);

  // const [data, setData] = useState<DataType[]>([]);
  const [data, setData] = useState<API.InterfaceInfo>();
  const params = useParams('/interface_info/:id');

  const [invokeRes, setInvokeRes] = useState<any>();
  const [invokeLoading, setInvokeLoading] = useState(false);
  const items: DescriptionsProps['items'] = [
    //     createTime?: string;
    //     description?: string;
    //     id?: number;
    //     isDelete?: number;
    //     method?: string;
    //     name?: string;
    //     requestHeader?: string;
    //     responseHeader?: string;
    //     status?: number;
    //     updateTime?: string;
    //     url?: string;
    //     userId?: number;\

    {
      key: '1',
      label: '接口状态',
      children:data?.status?<Badge status="processing" text="正常" />:<Badge status="error" text="关闭" />,
      span: 3
    },
    {
      key: '2',
      label: '接口描述',
      children: data?.description,
      span: 3
    },
    {
      key: '3',
      label: '请求地址',
      children: data?.url,

    },
    {
      key: '4',
      label: '请求参数',
      children: data?.requestParams,

    },
    {
      key: '5',
      label: '请求方法',
      children: data?.method,
    },
    {
      key: '6',
      label: '请求头',
      children: data?.requestHeader,
      span: 2,
    },
    {
      key: '7',
      label: '响应头',
      children: data?.responseHeader,
      span: 3,
    },
    {
      key: '8',
      label: '创建时间',
      children: data?.createTime,
    },
    {
      key: '9',
      label: '更新时间',
      children: data?.updateTime,
    }

  ];
  // alert(JSON.stringify(params))
  const loadData  = async(current:number = 1,pageSize:number =5) =>{
    if(!params.id){
      message.error('参数不存在');
      return ;
    }
    setLoading(true);
    try {
      const res = await getInterfaceInfoByIdUsingGet({
        id: Number(params.id)
      });
      setData(res.data);
    }catch (error:any) {
      message.error('请求失败' + error.message);
    }
    setLoading(false);
  }
  useEffect(()=>{
    loadData()
  },[])

  const onFinish  =async (values:any) => {
    if(!params.id){
      message.error('接口不存在');
      return ;
    }
    setInvokeLoading(true);
    try {
      const res = await invokeInterfaceInfoUsingPost({
        id:params.id,
        ...values
      })
      setInvokeRes(res.data);
      // alert("res.data --- "+res.data)
      // alert(invokeRes);
      message.success('请求成功');
      return true;
    } catch (error:any) {
      message.error('请求失败，' + error.message);
      return false;
    }
    setInvokeLoading(false);
  };

  const onFinishFailed: FormProps["onFinishFailed"] = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };
  return (
    <PageContainer title={"查看接口文档"}>
      <Card>
        {data?(
          <Descriptions title={data.name} column={2}  bordered items={items} />
        ):(
          <>接口不存在</>
        )
        }
      </Card>
      <Divider/>
      <Card>
        <Form layout={"vertical"}
          name="invoke"
          /*labelCol={{ span: 8 }}
          wrapperCol={{ span: 16 }}
          style={{ maxWidth: 600 }}
          initialValues={{ remember: true }}*/
          onFinish={onFinish}
          // onFinishFailed={onFinishFailed}
          // autoComplete="off"
        >
          <Form.Item
            label="用户调用接口请求参数"
            name="userRequestParams"
          >
            <Input.TextArea  />
          </Form.Item>

          <Form.Item wrapperCol={{ span: 10 }}>
            <Button type="primary" htmlType="submit">
              调用
            </Button>
          </Form.Item>
        </Form>
      </Card>
      <Divider/>
      <Card title="测试结果" loading={!invokeLoading} >
        {invokeRes}
      </Card>
    </PageContainer>
  );
};

export default Index;
