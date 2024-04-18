import { PageContainer } from '@ant-design/pro-components';
import {List, message, } from 'antd';
import React, {useEffect, useState} from 'react';
import {listInterfaceInfoByPageUsingGet} from "@/services/yunapi-backend/interfaceInfoController";


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
  const [list, setList] = useState<API.InterfaceInfo[]>([]);
  const [total,setTotal] = useState<number>(0);

  const loadData  = async(current:number = 1,pageSize:number =5) =>{
    setLoading(true);
    try {
      const res = await listInterfaceInfoByPageUsingGet({
        current,pageSize
      });
      setList(res?.data?.records ?? []);
      setTotal(res?.data?.total ?? 0);
    }catch (error:any) {
      message.error('请求失败' + error.message);
    }
    setLoading(false);
  }
  useEffect(()=>{
    loadData()
  },[])

  return (
    <PageContainer title={"云api开放平台"}>
      <List
        className="my-list"
        loading={loading}
        itemLayout="horizontal"
        // loadMore={loadMore}
        dataSource={list}
        renderItem={(item) => {
          const apiLink = `/interface_info/${item.id}`;
          return (<List.Item
            actions={[<a key={item.id} href={apiLink}>查看</a>]}
          >
            {/*<Skeleton avatar title={false} loading={item.loading} active>*/}
              <List.Item.Meta
                title={<a href={apiLink}>{item.name}</a>}
                description={item.description}
              />
              <div>content</div>
            {/*</Skeleton>*/}
          </List.Item>
          );
        }}
        pagination = {
          {
            showTotal(total) {
              return '总数：'+total;
            },
            pageSize : 5 ,
            total : total,
            onChange(page,pageSize){
              loadData(page,pageSize);
              }
          }
        }
      />
    </PageContainer>
  );
};

export default Index;
