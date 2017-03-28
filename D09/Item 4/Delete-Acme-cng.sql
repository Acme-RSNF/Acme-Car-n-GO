start transaction;

use `Acme-cng`;

revoke all privileges on `Acme-cng`.* from 'acme-user'@'%';
revoke all privileges on `Acme-cng`.* from 'acme-manager'@'%';

drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';

drop database `Acme-cng`;

commit;