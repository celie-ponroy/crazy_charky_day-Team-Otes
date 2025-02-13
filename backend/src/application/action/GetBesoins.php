<?php
namespace charly\application\action;

use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;
use charly\application\renderer\JsonRenderer;
use charly\core\service\interfaces\BesoinServiceInterface;

class GetBesoins extends AbstractAction
{
    protected BesoinServiceInterface $besoinService;

    public function __construct(BesoinServiceInterface $besoinService)
    {
        $this->besoinService = $besoinService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        try {
            // Récupérer les besoins du user
            $needs = $this->besoinService->getBesoins();

            // Vérifier si des besoins existent pour ce user
            if (empty($needs)) {
                return JsonRenderer::render($rs, 404, ['error' => 'Aucun besoin trouvé pour ce user.']);
            }

            // Affichage des besoins
            return JsonRenderer::render($rs, 200, $needs);

        } catch (\Exception $e) {
            return JsonRenderer::render($rs, 500, ['error' => 'Erreur interne du serveur.']);
        }
    }
}
