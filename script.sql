create database drivesmartdb;

use drivesmartdb;


create table student
(
	student_id int auto_increment
		primary key,
	name varchar(100) not null,
	nic varchar(12) not null,
	email varchar(100) not null,
	phone varchar(15) null,
	birthday date null,
	address varchar(255) null,
	constraint email
		unique (email),
	constraint nic
		unique (nic)
);

create table payment
(
	payment_id int auto_increment
		primary key,
	student_id int null,
	amount decimal(10,2) not null,
	amount_due decimal(10,2) null,
	received_date date null,
	status enum('pending', 'paid', 'overdue') not null,
	constraint payment_ibfk_1
		foreign key (student_id) references student (student_id)
			on update cascade on delete cascade
);

create index student_id
	on payment (student_id);

create table template
(
	template_id int auto_increment
		primary key,
	name varchar(50) not null,
	description varchar(255) not null
);

create table email
(
	email_id int auto_increment
		primary key,
	title varchar(100) null,
	subject varchar(100) null,
	sent_date datetime null,
	template_id int null,
	student_id int not null,
	constraint email_ibfk_1
		foreign key (template_id) references template (template_id)
			on update cascade on delete cascade,
	constraint fk_student_id
		foreign key (student_id) references student (student_id)
);

create index template_id
	on email (template_id);

create table user
(
	user_id int auto_increment
		primary key,
	username varchar(50) not null,
	password varchar(100) not null,
	role enum('admin', 'employee') not null,
	status enum('active', 'deactivate') default 'active' not null,
	constraint username
		unique (username)
);

create table employee
(
	employee_id int auto_increment
		primary key,
	user_id int null,
	name varchar(100) not null,
	email varchar(100) not null,
	phone varchar(15) not null,
	position enum('instructor', 'manager', 'staff') not null,
	nic varchar(20) null,
	salary double null,
	constraint email
		unique (email),
	constraint user_id
		unique (user_id),
	constraint employee_ibfk_1
		foreign key (user_id) references user (user_id)
			on update cascade on delete cascade
);

create table package
(
	package_id int auto_increment
		primary key,
	price decimal(10,2) not null,
	description text null,
	duration int null,
	employee_id int null,
	package_name varchar(255) not null,
	constraint package_ibfk_1
		foreign key (employee_id) references employee (employee_id)
			on update cascade on delete cascade
);

create index employee_id
	on package (employee_id);

create table package_enrollment
(
	enrollment_id int auto_increment
		primary key,
	student_id int null,
	package_id int null,
	enrollment_date date not null,
	status enum('active', 'completed', 'cancelled') default 'active' not null,
	constraint package_enrollment_ibfk_1
		foreign key (student_id) references student (student_id)
			on update cascade on delete cascade,
	constraint package_enrollment_ibfk_2
		foreign key (package_id) references package (package_id)
			on update cascade on delete cascade
);

create index package_id
	on package_enrollment (package_id);

create index student_id
	on package_enrollment (student_id);

create table resources
(
	resource_id int auto_increment
		primary key,
	package_id int null,
	title varchar(100) not null,
	url varchar(255) not null,
	constraint resources_ibfk_1
		foreign key (package_id) references package (package_id)
			on update cascade on delete cascade
);

create index package_id
	on resources (package_id);

create table vehicle
(
	vehicle_id int auto_increment
		primary key,
	vehicle_type enum('car', 'motorcycle', 'van', 'truck', 'bus') not null,
	make varchar(50) not null,
	model varchar(50) null,
	transmission enum('manual', 'automatic') not null,
	plate_number varchar(20) not null,
	constraint plate_number
		unique (plate_number)
);

create table session
(
	session_id int auto_increment
		primary key,
	package_id int null,
	session_date date not null,
	session_time time not null,
	employee_id int null,
	vehicle_id int null,
	student_id int null,
	constraint session_ibfk_1
		foreign key (package_id) references package (package_id)
			on update cascade on delete cascade,
	constraint session_ibfk_2
		foreign key (employee_id) references employee (employee_id)
			on update cascade on delete cascade,
	constraint session_ibfk_3
		foreign key (vehicle_id) references vehicle (vehicle_id)
			on update cascade on delete cascade
);

create index employee_id
	on session (employee_id);

create index package_id
	on session (package_id);

create index vehicle_id
	on session (vehicle_id);

create table session_attendance
(
	attendance_id int auto_increment
		primary key,
	session_id int null,
	status enum('present', 'absent') not null,
	constraint session_attendance_ibfk_1
		foreign key (session_id) references session (session_id)
			on update cascade on delete cascade
);

create index session_id
	on session_attendance (session_id);

create table vehicle_status
(
	status_id int auto_increment
		primary key,
	vehicle_id int null,
	status enum('available', 'unavailable') default 'available' not null,
	timestamp datetime not null,
	constraint vehicle_status_ibfk_1
		foreign key (vehicle_id) references vehicle (vehicle_id)
			on update cascade on delete cascade
);

create index vehicle_id
	on vehicle_status (vehicle_id);


