MagicTracker 0.0

����:@���ɴ� (����΢��: http://weibo.com/keanhu )

��Դ:���о���Դ��ĿԴ����ʱ,������־����(�� commons-logging )�Դ���ִ�й��̽��и���.����־�����޷�����ʵ������,����MagicTracker������.

����:�������̽ṹ�ǳ��� commons-logging ,ԭ��Ҳ����һģһ��.��ͬ����,commons-logging��Ե�����־����,MagicTracker��Ե�����Ϣ�׶�.

����:����̨�����Ϣ���в�νṹ,�ɶ��Ը���.ͨ�������ļ�,�����������Щ�׶ε���Ϣ,���������Ϊ�ı��ļ�,���������Ϊhtml�ļ�.

�����������(org/magictracker/test/TestDemo):

1.TestDemo ���Ӽ��

  t.begin(name,"...") �� t.stop() ����,�γ�һ���׶η�Χ,nameΪ�ý׶�����.

  t.r("..."),�����Ϣ,��commons-logging��info()��������.

  t.reportList(),���TestDemo�����������õ����н׶�����,��ط���TestDemo����ִ�е����λ��.

2.tracker-config.xml Ϊ�û������ļ�

  <visible-list> Ԫ��,����������ʾ��Щ�׶�,nameֵΪ�ý׶ε�����,��TestDemo.java�� t.begin(name,"...")ָ��.

  <handlers> Ԫ��,������Ϣ����ķ�ʽ,Ĭ��ֻ�ڿ���̨�����Ϣ.TxtHandler�������Ϊ�ı��ļ�,HtmlHandlerΪ���html�ļ�,���ǵ�pathֵΪ�ļ������·��,�����ȴ��������õ�Ŀ¼,��������׳��޷��ҵ�·�����쳣.����Ҫ�Ļ�,��ע�͵�.

3.build.xml ��Ant����,ֻ�� MagicTracker �����jar,�����ȱ���Դ�ļ�.

4.MagicTracker ������Щ��

commons-beanutils-1.7.0
commons-collections-3.1
commons-digester-1.7
commons-logging-1.0.4

5.���� TestDemo ���ɿ�����Ϣ���Ч��


