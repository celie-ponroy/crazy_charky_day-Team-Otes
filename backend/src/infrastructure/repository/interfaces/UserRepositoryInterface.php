<?php

namespace charly\infrastructure\repository\interfaces;

use charly\core\domain\entity\User;
use charly\core\dto\input\InputCreateSalarie;
use charly\core\dto\UserDTO;

interface UserRepositoryInterface
{
    public function createSalarie(InputCreateSalarie $inputCreateSalarie):User;
    public function getSalaries():array;


    
}