import {
  ModalForm, ProColumns,
  ProFormDateTimePicker,
  ProFormRadio,
  ProFormSelect,
  ProFormText,
  ProFormTextArea, ProTable,
  StepsForm,
} from '@ant-design/pro-components';
import '@umijs/max';
import {Modal} from 'antd';
import React from 'react';


export type Props = {
  columns: ProColumns<API.InterfaceInfo>[];
  onCancel: () => void;
  onSubmit: (values: API.InterfaceInfo) => Promise<void>;
  visible: boolean;
  /*updateModalOpen: boolean;
  values: Partial<API.RuleListItem>;*/
};
const CreateModal: React.FC<Props> = (props: Props) => {
  const {columns} = props
  return <Modal open={props.visible} onCancel={() => props.onCancel?.()}>
    <ProTable type="form"
              columns={columns}
              onSubmit={async (value)=>{
                props.onSubmit?.(value)

    }}/>
  </Modal>
};
export default CreateModal;
