insert into category_entity(id, icon_url, name, type)
values ('412b9c99-fb3c-4378-84fb-823c96f2ba5d', '08a23435-4fff-45cd-9ae6-eae306ba9c5d', 'Kirim', 'INPUT'),
       ('712b4734-bd65-49f8-95ca-4f394a2e059a', '08a23435-4fff-45cd-9ae6-eae306ba9c5d', 'Chiqim', 'OUTPUT');
insert into category_entity(id, icon_url, name, type, category_id)
values ('eee2e564-c4f3-4ea9-b8b0-6bf1239a26a2', 'bba97aa8-34c3-444b-af15-296b04de88f5', ': Business', 'INPUT',
        '412b9c99-fb3c-4378-84fb-823c96f2ba5d'),
       ('7369eef7-60fe-43fb-95c2-94e37c09b457', 'bba97aa8-34c3-444b-af15-296b04de88f5', 'Extra Income', 'INPUT',
        '412b9c99-fb3c-4378-84fb-823c96f2ba5d'),
       ('682db23d-ac6c-4278-8df2-8046af22db7e', 'bba97aa8-34c3-444b-af15-296b04de88f5', 'Gifts', 'INPUT',
        '412b9c99-fb3c-4378-84fb-823c96f2ba5d'),
       ('a308b956-0ae1-4938-89c6-6ffb99733fcf', 'bba97aa8-34c3-444b-af15-296b04de88f5', 'Other', 'INPUT',
        '412b9c99-fb3c-4378-84fb-823c96f2ba5d'),
       ('734e198f-6c76-4a48-868b-5340cf5b2320', 'bba97aa8-34c3-444b-af15-296b04de88f5', 'Food & Drink', 'OUTPUT',
        '712b4734-bd65-49f8-95ca-4f394a2e059a'),
       ('04e363fe-859d-420f-a7e5-05b8a8bfa0fc', 'bba97aa8-34c3-444b-af15-296b04de88f5', 'Gifts', 'OUTPUT',
        '712b4734-bd65-49f8-95ca-4f394a2e059a'),
       ('27d74842-7685-462c-ab5d-5c38ac80ace8', 'bba97aa8-34c3-444b-af15-296b04de88f5', 'Shopping', 'OUTPUT',
        '712b4734-bd65-49f8-95ca-4f394a2e059a'),
       ('f042a462-ca65-40f9-b5c3-04d44606a027', 'bba97aa8-34c3-444b-af15-296b04de88f5', 'Other', 'OUTPUT',
        '712b4734-bd65-49f8-95ca-4f394a2e059a');
insert into currency_entity(id,ccy,ccy_nm_uz,rate)
values (1,'SUM','SOM',1);


