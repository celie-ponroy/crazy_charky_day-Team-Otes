<?php

namespace charly\application\action;

use charly\application\renderer\JsonRenderer;
use charly\core\dto\input\InputCreateSalarie;
use charly\core\service\interfaces\UserServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class PostSalarie extends AbstractAction
{
    protected UserServiceInterface $userService;

    public function __construct(UserServiceInterface $userService)
    {
        $this->userService = $userService;
    }
    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        $data = $rq->getParsedBody();

        $nom = $data['nom'];
        $competences = $data['competences'];
        //competences = [ { id: 1, note : 5}, { id: 2, note : 3} ]

        $salarie = $this->userService->createSalarie(new InputCreateSalarie($nom, $competences));
        return JsonRenderer::render($rs, 200, $salarie);
    }
}