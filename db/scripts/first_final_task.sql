select p.name as Имя_сотрудника, c.name as Название_компании
from person as p
join company as c
on c.id = p.company_id
where c.id <> 5