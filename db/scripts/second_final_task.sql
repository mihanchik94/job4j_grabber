with pivot_table as (
select c.name as Название_компании, count(*) as Количество_сотрудников
from company as c
join person as p
on c.id = p.company_id
group by c.name)

select * from pivot_table
where Количество_сотрудников = (select max(Количество_сотрудников) from pivot_table)