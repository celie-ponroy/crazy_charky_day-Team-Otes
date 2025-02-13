<?php

namespace charly\core\service\interfaces;

use charly\core\dto\input\InputCreateSalarie;

interface UserServiceInterface
{
    public function createSalarie(InputCreateSalarie $inputCreateSalarie):void;
    public function getSalaries ():array;

    
}
