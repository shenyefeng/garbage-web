select gt.type_name, sum(g.weight) from t_garbages g
left join t_garbage_types gt on g.garbage_type_id=gt.id
group by gt.id;

select u.user_name, gt.type_name, sum(g.weight) from t_garbages g
left join t_qr_codes qc on qc.id=g.qr_code_id
left join t_users u on qc.user_id=u.id
left join t_garbage_types gt on g.garbage_type_id=gt.id
group by u.id, gt.id
order by u.id

