<?php

namespace charly\core\service\interfaces;

use charly\core\dto\input\InputCreateSalarie;

interface UserServiceInterface
{
    public function createSalarie(InputCreateSalarie $inputCreateSalarie);
    public function getSalaries ():array;
    public function getUserBesoins (string $idUser):array;

    public function getBesoins ():array;
    
}
